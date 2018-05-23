package com.toro_studio.schedule.model;

import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Date;

public class WeekCalViewPartsWeekModel {

    private int cachedX;
    private int cachedY;
    private Date displayDate;

    private String dayCount;
    private String weekCount;
    private RectF weekRect;
    private RectF dayRect;

    private boolean isHoliday;
    private boolean isFocused;

    private Paint dayPaint;
    private Paint weekPaint;
    private Paint dayTextPaint;
    private Paint weekTextPaint;
    private Paint focusedPaint;
    private Paint clearPaint;

    private float dayCenterX;
    private float dayCenterY;
    private float weekCenterX;
    private float weekCenterY;

    public final int getCachedX() {
        return cachedX;
    }

    public final void setCachedX(int cachedX) {
        this.cachedX = cachedX;
    }

    public final int getCachedY() {
        return cachedY;
    }

    public final void setCachedY(int cachedY) {
        this.cachedY = cachedY;
    }

    public final Date getDisplayDate() {
        return displayDate;
    }

    public final void setDisplayDate(Date displayDate) {
        this.displayDate = displayDate;
    }

    public final String getDayCount() {
        return dayCount;
    }

    public final void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public final String getWeekCount() {
        return weekCount;
    }

    public final void setWeekCount(String weekCount) {
        this.weekCount = weekCount;
    }

    public final RectF getWeekRect() {
        return weekRect;
    }

    public final void setWeekRect(RectF weekRect) {
        this.weekRect = weekRect;
    }

    public final RectF getDayRect() {
        return dayRect;
    }

    public final void setDayRect(RectF dayRect) {
        this.dayRect = dayRect;
    }

    public final boolean isHoliday() {
        return isHoliday;
    }

    public final void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public final boolean isFocused() {
        return isFocused;
    }

    public final void setFocused(boolean focused) {
        isFocused = focused;
    }

    public final Paint getDayPaint() {
        return dayPaint;
    }

    public final void setDayPaint(Paint dayPaint) {
        this.dayPaint = dayPaint;
    }

    public final Paint getWeekPaint() {
        return weekPaint;
    }

    public final void setWeekPaint(Paint weekPaint) {
        this.weekPaint = weekPaint;
    }

    public final Paint getDayTextPaint() {
        return dayTextPaint;
    }

    public final void setDayTextPaint(Paint dayTextPaint) {
        this.dayTextPaint = dayTextPaint;
    }

    public final Paint getWeekTextPaint() {
        return weekTextPaint;
    }

    public final void setWeekTextPaint(Paint weekTextPaint) {
        this.weekTextPaint = weekTextPaint;
    }

    public final Paint getFocusedPaint() {
        return focusedPaint;
    }

    public final void setFocusedPaint(Paint focusedPaint) {
        this.focusedPaint = focusedPaint;
    }

    public final Paint getClearPaint() {
        return clearPaint;
    }

    public final void setClearPaint(Paint clearPaint) {
        this.clearPaint = clearPaint;
    }

    public final float getDayCenterX() {
        return dayCenterX;
    }

    public final void setDayCenterX(float dayCenterX) {
        this.dayCenterX = dayCenterX;
    }

    public final float getDayCenterY() {
        return dayCenterY;
    }

    public final void setDayCenterY(float dayCenterY) {
        this.dayCenterY = dayCenterY;
    }

    public final float getWeekCenterX() {
        return weekCenterX;
    }

    public final void setWeekCenterX(float weekCenterX) {
        this.weekCenterX = weekCenterX;
    }

    public final float getWeekCenterY() {
        return weekCenterY;
    }

    public final void setWeekCenterY(float weekCenterY) {
        this.weekCenterY = weekCenterY;
    }
}
