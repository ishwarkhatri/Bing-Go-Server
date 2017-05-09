package com.go.bing.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import org.springframework.data.annotation.Id;

@Entity
public class Event {

	private @Id @GeneratedValue String id;
	private String name;
	private Date startDate;
	private String startDateString;
	private Date endDate;
	private String endDateString;
	private int startHour;
	private int startMin;
	private int endHour;
	private int endMin;
	private String location;
	private String description;
	private boolean isOngoing;
	private boolean isPast;
	private boolean isUpcoming;

	public boolean isOngoing() {
		return isOngoing;
	}
	public void setOngoing(boolean isOngoing) {
		this.isOngoing = isOngoing;
	}
	public boolean isPast() {
		return isPast;
	}
	public void setPast(boolean isPast) {
		this.isPast = isPast;
	}
	public boolean isUpcoming() {
		return isUpcoming;
	}
	public void setUpcoming(boolean isUpcoming) {
		this.isUpcoming = isUpcoming;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDateString() {
		return startDateString;
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public String getEndDateString() {
		return endDateString;
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getStartMin() {
		return startMin;
	}
	public void setStartMin(int startMin) {
		this.startMin = startMin;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public int getEndMin() {
		return endMin;
	}
	public void setEndMin(int endMin) {
		this.endMin = endMin;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
