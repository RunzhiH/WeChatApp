package com.jisu.WeChatApp.pojo;

import java.util.Date;
import java.math.BigDecimal;

public class RefundOrderInfo {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refund_order_id
	 * @mbg.generated
	 */
	private String refundOrderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refund_order_status
	 * @mbg.generated
	 */
	private Integer refundOrderStatus;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refund_order_desc
	 * @mbg.generated
	 */
	private String refundOrderDesc;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.shop_server_id
	 * @mbg.generated
	 */
	private String shopServerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.create_time
	 * @mbg.generated
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.agreed_time
	 * @mbg.generated
	 */
	private Date agreedTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refuse_time
	 * @mbg.generated
	 */
	private Date refuseTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refund_time
	 * @mbg.generated
	 */
	private Date refundTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.finish_time
	 * @mbg.generated
	 */
	private Date finishTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refuse_note
	 * @mbg.generated
	 */
	private String refuseNote;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.order_id
	 * @mbg.generated
	 */
	private String orderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.refund_price
	 * @mbg.generated
	 */
	private BigDecimal refundPrice;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column refund_order_info.order_price
	 * @mbg.generated
	 */
	private BigDecimal orderPrice;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refund_order_id
	 * @return  the value of refund_order_info.refund_order_id
	 * @mbg.generated
	 */
	public String getRefundOrderId() {
		return refundOrderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refund_order_id
	 * @param refundOrderId  the value for refund_order_info.refund_order_id
	 * @mbg.generated
	 */
	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId == null ? null : refundOrderId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refund_order_status
	 * @return  the value of refund_order_info.refund_order_status
	 * @mbg.generated
	 */
	public Integer getRefundOrderStatus() {
		return refundOrderStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refund_order_status
	 * @param refundOrderStatus  the value for refund_order_info.refund_order_status
	 * @mbg.generated
	 */
	public void setRefundOrderStatus(Integer refundOrderStatus) {
		this.refundOrderStatus = refundOrderStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refund_order_desc
	 * @return  the value of refund_order_info.refund_order_desc
	 * @mbg.generated
	 */
	public String getRefundOrderDesc() {
		return refundOrderDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refund_order_desc
	 * @param refundOrderDesc  the value for refund_order_info.refund_order_desc
	 * @mbg.generated
	 */
	public void setRefundOrderDesc(String refundOrderDesc) {
		this.refundOrderDesc = refundOrderDesc == null ? null : refundOrderDesc.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.shop_server_id
	 * @return  the value of refund_order_info.shop_server_id
	 * @mbg.generated
	 */
	public String getShopServerId() {
		return shopServerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.shop_server_id
	 * @param shopServerId  the value for refund_order_info.shop_server_id
	 * @mbg.generated
	 */
	public void setShopServerId(String shopServerId) {
		this.shopServerId = shopServerId == null ? null : shopServerId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.create_time
	 * @return  the value of refund_order_info.create_time
	 * @mbg.generated
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.create_time
	 * @param createTime  the value for refund_order_info.create_time
	 * @mbg.generated
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.agreed_time
	 * @return  the value of refund_order_info.agreed_time
	 * @mbg.generated
	 */
	public Date getAgreedTime() {
		return agreedTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.agreed_time
	 * @param agreedTime  the value for refund_order_info.agreed_time
	 * @mbg.generated
	 */
	public void setAgreedTime(Date agreedTime) {
		this.agreedTime = agreedTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refuse_time
	 * @return  the value of refund_order_info.refuse_time
	 * @mbg.generated
	 */
	public Date getRefuseTime() {
		return refuseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refuse_time
	 * @param refuseTime  the value for refund_order_info.refuse_time
	 * @mbg.generated
	 */
	public void setRefuseTime(Date refuseTime) {
		this.refuseTime = refuseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refund_time
	 * @return  the value of refund_order_info.refund_time
	 * @mbg.generated
	 */
	public Date getRefundTime() {
		return refundTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refund_time
	 * @param refundTime  the value for refund_order_info.refund_time
	 * @mbg.generated
	 */
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.finish_time
	 * @return  the value of refund_order_info.finish_time
	 * @mbg.generated
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.finish_time
	 * @param finishTime  the value for refund_order_info.finish_time
	 * @mbg.generated
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refuse_note
	 * @return  the value of refund_order_info.refuse_note
	 * @mbg.generated
	 */
	public String getRefuseNote() {
		return refuseNote;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refuse_note
	 * @param refuseNote  the value for refund_order_info.refuse_note
	 * @mbg.generated
	 */
	public void setRefuseNote(String refuseNote) {
		this.refuseNote = refuseNote == null ? null : refuseNote.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.order_id
	 * @return  the value of refund_order_info.order_id
	 * @mbg.generated
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.order_id
	 * @param orderId  the value for refund_order_info.order_id
	 * @mbg.generated
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId == null ? null : orderId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.refund_price
	 * @return  the value of refund_order_info.refund_price
	 * @mbg.generated
	 */
	public BigDecimal getRefundPrice() {
		return refundPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.refund_price
	 * @param refundPrice  the value for refund_order_info.refund_price
	 * @mbg.generated
	 */
	public void setRefundPrice(BigDecimal refundPrice) {
		this.refundPrice = refundPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column refund_order_info.order_price
	 * @return  the value of refund_order_info.order_price
	 * @mbg.generated
	 */
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column refund_order_info.order_price
	 * @param orderPrice  the value for refund_order_info.order_price
	 * @mbg.generated
	 */
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
}