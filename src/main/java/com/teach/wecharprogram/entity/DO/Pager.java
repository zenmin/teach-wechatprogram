package com.teach.wecharprogram.entity.DO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

import java.security.interfaces.ECKey;
import java.util.List;

/**
 * @Describle This Class Is 分页实体
 * @Author ZengMin
 * @Date 2019/3/15 9:40
 */
@AllArgsConstructor
@NoArgsConstructor
public class Pager<T> {

    @ApiModelProperty(value = "页码", example = "1")
    private long num = 1;

    @ApiModelProperty(value = "分页大小", example = "10")
    private long size = 15;

    @ApiModelProperty(hidden = true)
    private List<T> data;

    @ApiModelProperty(hidden = true)
    private boolean last;

    @ApiModelProperty(hidden = true)
    private long totalNums;

    @ApiModelProperty(hidden = true)
    private long totalPages;

    public Pager(int num, int size) {
        this.num = num;
        this.size = size;
    }

    public static Pager of(IPage iPage) {
        return new Pager<>(iPage.getCurrent(), iPage.getSize(), iPage.getRecords(), iPage.getPages() <= iPage.getCurrent(), iPage.getTotal(), iPage.getPages());
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        if (num <= 1) {
            this.num = 0L;
        } else {
            this.num = num;
        }
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        if (size > 999) {
            this.size = 999;
        } else {
            this.size = size;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public long getTotalNums() {
        return totalNums;
    }

    public void setTotalNums(long totalNums) {
        this.totalNums = totalNums;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }
}
