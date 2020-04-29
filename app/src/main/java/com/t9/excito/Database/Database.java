package com.t9.excito.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.ContactsContract;
import android.widget.LinearLayout;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.t9.excito.Models.Order;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="ExcitoDB.db.db";
    private static final int DB_VER=1;

    public Database(Context context){
        super(context, DB_NAME,null,DB_VER);
    }
    public List<Order> getCarts(){
        SQLiteDatabase db= getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();

        String[] sqlSelect={"StoreName","ProductName", "Price", "Quantity"};
        String sqlTable="OrderDetail";
        qb.setTables(sqlTable);
        Cursor c= qb.query(db, sqlSelect, null, null, null,null, null);

        final List<Order> result=new ArrayList<>();
        if(c.moveToFirst()){
            do {
                result.add(new Order(c.getString(c.getColumnIndex("StoreName")),
                        c.getString(c.getColumnIndex("StoreName")),
                        c.getString(c.getColumnIndex("StoreName")),
                        c.getString(c.getColumnIndex("StoreName"))
                ));
            }while (c.moveToNext());

            }
        return result;
        }

        public void addToCart(Order order){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO OrderDetail(StoreName, ProductName, Quantity, Price) Values('%s' ,'%s' ,'%s' ,'%s');",
                order.getStoreName(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());
        db.execSQL(query);
        }
    public void cleanCart(Order order){
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM OrderDetail");

        db.execSQL(query);
    }
    }


