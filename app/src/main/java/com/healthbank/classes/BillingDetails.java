package com.healthbank.classes;

import java.io.Serializable;

/**
 * Created by it1 on 8/27/2018.
 */

public class BillingDetails implements Serializable {
    String item = "";
    String billingId = "";
    String offerid = "";
    String tax = "";
    String taxid = "";
    String producttype = "";
    String stockid = "";
    String assignby = "";
    String item_id = "";
    String unit = "";
    String unitcost = "";
    String disamount = "";
    String taxamount = "";
    String taxtype = "";
    String distype = "";
    String treatmentdate = "";
    String pack_id = "";
    String indextempcol = "";
    String counselingid = "";
    String profileid = "";

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getBillingId() {
        return billingId;
    }

    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    public String getOfferid() {
        return offerid;
    }

    public void setOfferid(String offerid) {
        this.offerid = offerid;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTaxid() {
        return taxid;
    }

    public void setTaxid(String taxid) {
        this.taxid = taxid;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getStockid() {
        return stockid;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    public String getAssignby() {
        return assignby;
    }

    public void setAssignby(String assignby) {
        this.assignby = assignby;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitcost() {
        return unitcost;
    }

    public void setUnitcost(String unitcost) {
        this.unitcost = unitcost;
    }

    public String getDisamount() {
        return disamount;
    }

    public void setDisamount(String disamount) {
        this.disamount = disamount;
    }

    public String getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(String taxamount) {
        this.taxamount = taxamount;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public String getDistype() {
        return distype;
    }

    public void setDistype(String distype) {
        this.distype = distype;
    }

    public String getTreatmentdate() {
        return treatmentdate;
    }

    public void setTreatmentdate(String treatmentdate) {
        this.treatmentdate = treatmentdate;
    }

    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }

    public String getIndextempcol() {
        return indextempcol;
    }

    public void setIndextempcol(String indextempcol) {
        this.indextempcol = indextempcol;
    }

    public String getCounselingid() {
        return counselingid;
    }

    public void setCounselingid(String counselingid) {
        this.counselingid = counselingid;
    }

    public String getProfileid() {
        return profileid;
    }

    public void setProfileid(String profileid) {
        this.profileid = profileid;
    }
}
