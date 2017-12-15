package com.odoo.addons.sales.models;

import android.content.Context;
import android.net.Uri;

import com.odoo.base.addons.res.ResPartner;
import com.odoo.core.orm.OModel;
import com.odoo.core.orm.fields.OColumn;
import com.odoo.core.orm.fields.types.OVarchar;
import com.odoo.core.support.OUser;

/**
 * Created by lavrentievd on 15.12.2017.
 */

public class SaleOrder extends OModel {
    public static final String TAG = SaleOrder.class.getSimpleName();
    public static final String AUTHORITY = "com.odoo.addons.sale.sale_order";

    OColumn name = new OColumn("Name", OVarchar.class).setSize(100);
    OColumn date_order = new OColumn("Date", OVarchar.class);
    OColumn user_id = new OColumn("Salesperson", OVarchar.class);
    OColumn partner_id = new OColumn("Customer", ResPartner.class, OColumn.RelationType.ManyToOne);

    public SaleOrder(Context context, OUser user) {
        super(context, "sale.order", user);
    }

    @Override
    public Uri uri() {
        return buildURI(AUTHORITY);
    }

}
