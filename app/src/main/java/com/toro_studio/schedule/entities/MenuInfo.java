package com.toro_studio.schedule.entities;

public class MenuInfo {

    private int id;
    private String menuText;
    private int resourceId;
    private String iconColor;

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getMenuText() {
        return menuText;
    }

    public final void setMenuText(String menuText) {
        this.menuText = menuText;
    }

    public final int getResourceId() {
        return resourceId;
    }

    public final void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public final String getIconColor() {
        return iconColor;
    }

    public final void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "id=" + id +
                ", menuText='" + menuText + '\'' +
                ", resourceId=" + resourceId +
                ", iconColor='" + iconColor + '\'' +
                '}';
    }

}
