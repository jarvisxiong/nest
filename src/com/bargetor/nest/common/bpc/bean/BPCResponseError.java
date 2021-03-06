/**
 * bargetorCommon
 * com.bargetor.nest.common.bpc
 * BPCResponseError.java
 * 
 * 2015年5月13日-下午11:03:58
 *  2015Bargetor-版权所有
 *
 */
package com.bargetor.nest.common.bpc.bean;

/**
 *
 * BPCResponseError
 * bpc 协议错误
 * kin
 * kin
 * 2015年5月13日 下午11:03:58
 * 
 * @version 1.0.0
 *
 */
public class BPCResponseError extends RuntimeException{
	/**
	 * serialVersionUID:TODO（用一句话描述这个变量表示什么）
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 1L;
	private int status;
	private String msg;
	
	/**
	 * 创建一个新的实例 BPCResponseError.
	 *
	 */
	public BPCResponseError() {
		// TODO Auto-generated constructor stub
	}

	public BPCResponseError(Exception e) {
		super(e);
	}

	public BPCResponseError(String msg) {
		super(msg);
	}

	public BPCResponseError(Exception e, String msg) {
		super(msg, e);
	}
	
	public BPCResponseError(int status, String msg){
		super(msg);
		this.status = status;
		this.msg = msg;
	}
	/**
	 * status
	 *
	 * @return  the status
	 * @since   1.0.0
	 */
	
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * msg
	 *
	 * @return  the msg
	 * @since   1.0.0
	 */
	
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	

}
