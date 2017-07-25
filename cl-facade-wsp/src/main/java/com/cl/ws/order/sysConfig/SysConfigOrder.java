package com.cl.ws.order.sysConfig;


import java.util.Date;

import com.cl.ws.info.base.BaseToStringInfo;
import com.cl.ws.order.base.ProcessOrder;

/**
 * 系统配置
 * @author heh
 */
public class SysConfigOrder extends ProcessOrder {

	private int id;

	private String backgroundImage;

	private String carouselFigure1;

	private String carouselFigure2;

	private String carouselFigure3;

	private String carouselFigure4;

	private String carouselFigure5;

	private Date rawAddTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getCarouselFigure1() {
		return carouselFigure1;
	}

	public void setCarouselFigure1(String carouselFigure1) {
		this.carouselFigure1 = carouselFigure1;
	}

	public String getCarouselFigure2() {
		return carouselFigure2;
	}

	public void setCarouselFigure2(String carouselFigure2) {
		this.carouselFigure2 = carouselFigure2;
	}

	public String getCarouselFigure3() {
		return carouselFigure3;
	}

	public void setCarouselFigure3(String carouselFigure3) {
		this.carouselFigure3 = carouselFigure3;
	}

	public String getCarouselFigure4() {
		return carouselFigure4;
	}

	public void setCarouselFigure4(String carouselFigure4) {
		this.carouselFigure4 = carouselFigure4;
	}

	public String getCarouselFigure5() {
		return carouselFigure5;
	}

	public void setCarouselFigure5(String carouselFigure5) {
		this.carouselFigure5 = carouselFigure5;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
}
