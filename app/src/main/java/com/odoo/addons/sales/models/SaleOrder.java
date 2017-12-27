package com.odoo.addons.sales.models;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.odoo.base.addons.res.ResPartner;
import com.odoo.base.addons.res.ResUsers;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.ODateTime;
import com.odoo.core.orm.fields.types.OSelection;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

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
    OColumn user_id = new OColumn("Salesperson", ResUsers.class, OColumn.RelationType.ManyToOne);


    public SaleOrder(Context context, OUser user) {
        super(context, "sale.order", user);
    }

    @Override
    public Uri uri() {
        return buildURI(AUTHORITY);
    }

}
