package com.example.demo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * 文件名:AddressBean
 * 创建者:zed
 * 创建日期:2019/4/16 14:52
 * 描述:TODO
 */
@Entity(
        nameInDb = "address",
        createInDb = false
)
public class AddressBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    @Property(nameInDb = "ID")
    private Long id;
    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "PID")
    private int pid;
    @Property(nameInDb = "TYPE")
    private int type;
    @Property(nameInDb = "ISCHILD")
    private String isChild;

    @Generated(hash = 1012870687)
    public AddressBean(Long id, String name, int pid, int type, String isChild) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.type = type;
        this.isChild = isChild;
    }

    @Generated(hash = 30780671)
    public AddressBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return this.pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIsChild() {
        return this.isChild;
    }

    public void setIsChild(String isChild) {
        this.isChild = isChild;
    }

}
