package com.restapi.roombooking.pojo;

public class Room {
	
	private BookingDates bookingdates;
	private boolean depositpaid;
	private String firstname;
	private String lastname;
	private int roomid;
	private int totalprice;
	
	public BookingDates getBookingdates() {
		return bookingdates;
	}
	
	public void setBookingdates(BookingDates bookingdates) {
		this.bookingdates = bookingdates;
	}
	
	public boolean isDepositpaid() {
		return depositpaid;
	}
	
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public int getRoomid() {
		return roomid;
	}
	
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	
	public int getTotalprice() {
		return totalprice;
	}
	
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

}
