package com.hawke38645.groceryapp.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hawke38645.groceryapp.app.Config.Companion.DATABASE_NAME
import com.hawke38645.groceryapp.app.Config.Companion.DATABASE_VERSION
import com.hawke38645.groceryapp.app.MyApplication
import com.hawke38645.groceryapp.models.CartItem
import com.hawke38645.groceryapp.models.OrderSum

class DatabaseHelper() : SQLiteOpenHelper(
    MyApplication.instance, DATABASE_NAME,
    null, DATABASE_VERSION
) {

    var database: SQLiteDatabase = writableDatabase
    var checkList: ArrayList<CartItem> = ArrayList()

    companion object {
        const val TABLE_NAME = "Cart"
        const val COLUMN_PRODUCT_ID = "_id"
        const val COLUMN_PRODUCT_NAME = "productName"
        const val COLUMN_PRODUCT_PRICE = "price"
        const val COLUMN_PRODUCT_MRP = "mrp"
        const val COLUMN_PRODUCT_IMAGE_URL = "imageURL"
        const val COLUMN_PRODUCT_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table $TABLE_NAME (" +
                "$COLUMN_PRODUCT_ID char(30) PRIMARY KEY, " +
                "$COLUMN_PRODUCT_NAME char(70), " +
                "$COLUMN_PRODUCT_PRICE REAL, " +
                "$COLUMN_PRODUCT_MRP REAL, " +
                "$COLUMN_PRODUCT_IMAGE_URL char(200)," +
                "$COLUMN_PRODUCT_QUANTITY INTEGER)"
        db?.execSQL(createTable)
        Log.d("LogD: ", "OnCreate called: Database Created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("LogD: ", "OnUpgrade called: Database Upgraded")
    }

    fun addCartItem(cartItem: CartItem) {
        var contentValues = ContentValues()

        if (!isItemInCart(cartItem._id)) {
            contentValues.put(COLUMN_PRODUCT_ID, cartItem._id)
            contentValues.put(COLUMN_PRODUCT_NAME, cartItem.productName)
            contentValues.put(COLUMN_PRODUCT_PRICE, cartItem.price)
            contentValues.put(COLUMN_PRODUCT_MRP, cartItem.mrp)
            contentValues.put(COLUMN_PRODUCT_IMAGE_URL, cartItem.imageURL)
            contentValues.put(COLUMN_PRODUCT_QUANTITY, cartItem.quantity)

            database.insert(TABLE_NAME, null, contentValues)
            Log.d(
                "LogD: ",
                "CartItem added to DB. ID: ${cartItem._id}, NAME: ${cartItem.productName}"
            )
        } else {
            updateCartItem(cartItem, true)
        }

    }

    fun deleteCartItem(deleteCartItem: CartItem) {
        var whereClause = "$COLUMN_PRODUCT_ID=?"
        var whereArgs = arrayOf(deleteCartItem._id)
        database.delete(TABLE_NAME, whereClause, whereArgs)

        Log.d(
            "LogD:",
            "CartItem with  to DB. ID: ${deleteCartItem._id}, Name:${deleteCartItem.productName} deleted."
        )
    }

     fun isItemInCart(cartItemId: String): Boolean {
        val query = "Select * from $TABLE_NAME where $COLUMN_PRODUCT_ID=?"
        val cursor = database.rawQuery(query, arrayOf(cartItemId))
        var count = cursor.count
        cursor.close()
        return count != 0
    }

    fun getCartItemQuantity(cartItemId: String): Int {
        var quantity = 0
        if (isItemInCart(cartItemId)) {
            //TODO: Use Database to get item quantity and return it for display
            val query = "Select * from $TABLE_NAME where $COLUMN_PRODUCT_ID=?"
            val cursor = database.rawQuery(query, arrayOf(cartItemId))
            if (cursor != null && cursor.moveToNext()) {
                quantity =
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY)).toInt()
                cursor.close()
                return quantity
            }
        }
        return quantity
    }

    fun getAllItemQuantity(): Int {
        var totalQuantity = 0
        var columns = arrayOf(COLUMN_PRODUCT_QUANTITY)
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var cartItemQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))
                totalQuantity += cartItemQuantity
            } while (cursor.moveToNext())
            return totalQuantity
        }
        return totalQuantity
    }

    fun updateCartItem(updateCartItem: CartItem, increment: Boolean) {
        //Update the quantity of the item in the DATABASE
        val query = "Select * from $TABLE_NAME where $COLUMN_PRODUCT_ID=?"
        val cursor = database.rawQuery(query, arrayOf(updateCartItem._id))

        if (cursor != null && cursor.moveToNext()) {
            var quantity = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY)).toInt()
            //figure out a way to increment or decrement on a button press
            if (increment) {
                quantity += 1
            } else {
                if (quantity <= 1) {
                    deleteCartItem(updateCartItem)
                } else {
                    quantity -= 1
                }
            }

            var whereClause = "$COLUMN_PRODUCT_ID=?"
            var whereArgs = arrayOf(updateCartItem._id)
            var contentValues = ContentValues()

            contentValues.put(COLUMN_PRODUCT_QUANTITY, quantity)

            database.update(TABLE_NAME, contentValues, whereClause, whereArgs)
            Log.d("LogD:", "Product with ID ${updateCartItem._id} quantity is now ${quantity}.")
        }
    }

    fun readCartItems(): ArrayList<CartItem> {
        var mList: ArrayList<CartItem> = ArrayList()
        var columns = arrayOf(
            COLUMN_PRODUCT_ID,
            COLUMN_PRODUCT_NAME,
            COLUMN_PRODUCT_PRICE,
            COLUMN_PRODUCT_MRP,
            COLUMN_PRODUCT_IMAGE_URL,
            COLUMN_PRODUCT_QUANTITY
        )

        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var cartItemId = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID))
                var cartItemName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                var cartItemPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE))
                var cartItemMrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_MRP))
                var cartItemImage =
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMAGE_URL))
                var cartItemQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))

                var cartItem = CartItem(
                    cartItemId,
                    cartItemName,
                    cartItemPrice,
                    cartItemMrp,
                    cartItemImage,
                    cartItemQuantity
                )

                mList.add(cartItem)

            } while (cursor.moveToNext())
        }
        cursor.close()
        return mList
    }

    fun getCartItem(cartItemName: String): CartItem? {
        val query = "Select * from $TABLE_NAME where $COLUMN_PRODUCT_NAME=?"
        val cursor = database.rawQuery(query, arrayOf(cartItemName))

        if (cursor != null && cursor.moveToFirst()) {
                var cartItemId = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ID))
                var cartItemName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                var cartItemPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE))
                var cartItemMrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_MRP))
                var cartItemImage =
                    cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMAGE_URL))
                var cartItemQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))

            cursor.close()
            return CartItem(
                cartItemId,
                cartItemName,
                cartItemPrice,
                cartItemMrp,
                cartItemImage,
                cartItemQuantity
            )
        } else {
            cursor.close()
            return null
        }

    }

    //Select * from product                     -> This will read ALL rows AND ALL columns.
    //Select id, name, quantity from product    -> This will read SOME columns and ALL rows.
    //Select * from product where id=1          -> This will read ALL columns and SOME rows.
    //Select id, name from product where id=1   -> This will read SOME columns and SOME rows.

    fun calculateOrderSummary(): OrderSum {
        var columns = arrayOf(COLUMN_PRODUCT_PRICE, COLUMN_PRODUCT_MRP, COLUMN_PRODUCT_QUANTITY)
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)

        var totalPrice = 0.0
        var totalMrp = 0.0
        var discount = 0

        if (cursor != null && cursor.moveToFirst()) {
            do {
                var cartItemPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE))
                var cartItemMrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_MRP))
                var cartItemQuantity = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))

                totalPrice += cartItemPrice * cartItemQuantity
                totalMrp += cartItemMrp * cartItemQuantity

            } while (cursor.moveToNext())

            discount = (totalMrp - totalPrice).toInt()

        }
        return OrderSum(totalPrice, totalMrp, discount)
    }
}