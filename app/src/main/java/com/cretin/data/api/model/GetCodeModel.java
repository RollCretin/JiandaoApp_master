package com.cretin.data.api.model;

/**
 * Created by cretin on 2017/8/22.
 */

public class GetCodeModel {


    /**
     * id : null
     * name : hhh
     * price : null
     * pic : null
     * createtime : null
     * detail : null
     */

    private Object id;
    private String name;
    private Object price;
    private Object pic;
    private Object createtime;
    private Object detail;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getPic() {
        return pic;
    }

    public void setPic(Object pic) {
        this.pic = pic;
    }

    public Object getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Object createtime) {
        this.createtime = createtime;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
