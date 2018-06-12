package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.Goods;

import java.util.Date;

public class GoodsVo extends Goods {
    /* *
     * 通过继承Goods再加上一些需要的MiaoshaGoods的属性,达到把商品和秒杀订单整合到一块
     */
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private Double miaoshaPrice;

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }


    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
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
}
