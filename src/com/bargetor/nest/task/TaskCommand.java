package com.bargetor.nest.task;

import com.bargetor.nest.common.executor.RunableTask;
import com.bargetor.nest.common.util.ArrayUtil;
import com.bargetor.nest.common.util.ExceptionUtil;
import com.bargetor.nest.task.annotation.TaskRetryExceptions;
import com.bargetor.nest.task.bean.Task;
import com.bargetor.nest.task.bean.TaskError;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bargetor on 16/4/9.
 */
public abstract class TaskCommand implements Runnable{
    public abstract Task getOneTask();

    public abstract void execute(Task task);

    @Override
    public final void run() {
        Task task = this.getOneTask();
        if(task == null || task.getTaskId() == null)return;

        BigInteger taskId = task.getTaskId();

        TaskManager.getInstance().taskRuning(taskId);
        try{
            this.execute(task);
            TaskManager.getInstance().taskDone(taskId);
        }catch (Exception e){
            TaskError taskError = new TaskError();

            taskError.setMsg(String.format(
                    "[%s]->%s, stack:%s",
                    e.getClass().getName(),
                    e.getMessage(),
                    ExceptionUtil.getExceptionStackTraceString(e))
            );

            if(this.isInRetryExceptions(this.getRetryExceptions(), e)){
                TaskManager.getInstance().taskRetry(taskId, taskError);
            }else{
                TaskManager.getInstance().taskError(taskId, taskError);
            }
        }

        this.finallyMethod();
    }

    public void finallyMethod(){}

    private Class<? extends Exception>[] getRetryExceptions(){
        try {
            Method executeMethod = this.getClass().getMethod("execute", Task.class);
            TaskRetryExceptions retryAnnotation = executeMethod.getAnnotation(TaskRetryExceptions.class);
            return retryAnnotation.value();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 判断exception是否在retry exceptions中
     * @param retryExceptions
     * @param exception
     * @return
     */
    private boolean isInRetryExceptions(Class<? extends Exception>[] retryExceptions, Exception exception){
        if(retryExceptions == null || retryExceptions.length <= 0)return false;
        for (Class<? extends Exception> retryException : retryExceptions) {
            if(retryException.equals(exception.getClass()))return true;
        }
        return false;
    }
}
