package com.odoo.addons.sales.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.odoo.base.addons.res.ResCompany;
import com.odoo.base.addons.res.ResPartner;
import com.odoo.base.addons.res.ResUsers;
import com.odoo.core.orm.ODataRow;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.OValues;
import com.odoo.core.orm.annotation.Odoo;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.ODateTime;
import com.odoo.core.orm.fields.types.OSelection;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lavrentievd on 15.12.2017.
 */

public class SaleOrder extends OModel {
    public static final String TAG = SaleOrder.class.getSimpleName();
    public static final String AUTHORITY = "com.odoo.addons.sale.sale_order";

    OColumn name = new OColumn("Name", OVarchar.class).setSize(100).setRequired();
    OColumn state = new OColumn( "State", OSelection.class)
            .addSelection("draft","Draft")
            .addSelection("sent","Sent")
            .addSelection("cancel","Canceled")
            .addSelection("progress","Sale Oder")
            .addSelection("manual","Sale to Invoice")
            .addSelection("invoice_except","Invoice Exception")
            .addSelection("done","Done");
    OColumn date_order = new OColumn("Order Date", ODateTime.class);
    OColumn partner_id = new OColumn("Customer", ResPartner.class, OColumn.RelationType.ManyToOne).setRequired();

    OColumn user_id = new OColumn("Salesperson", ResUsers.class, OColumn.RelationType.ManyToOne);
            //.addDomain("user_id", "=", this.getUser().getUserId());

    @Odoo.Functional(store = true, depends = {"partner_id"}, method = "storePartner")
    OColumn partner_name = new OColumn("Partner Name",  OVarchar.class).setSize(100)
            .setLocalColumn();

    @Odoo.Functional(store = true, depends = {"user_id"}, method = "storeSaleperson")
    OColumn user_name = new OColumn("Salesperson Name", OVarchar.class)
            .setLocalColumn();

    public SaleOrder(Context context, OUser user) {
        super(context, "sale.order", user);

    }

    @Override
    public Uri uri() {
        return buildURI(AUTHORITY);
    }

    public String storePartner(OValues value) {
        try {
            if (!value.getString("partner_id").equals("false")) {
                List<Object> partner_id = (ArrayList<Object>) value.get("partner_id");
                return partner_id.get(1) + "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String storeSaleperson(OValues value) {
        try {
            if (!value.getString("user_id").equals("false")) {
                List<Object> user_id = (ArrayList<Object>) value.get("user_id");
                return user_id.get(1) + "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
