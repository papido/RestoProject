package com.fauzan.restoapp.Repository

import com.fauzan.restoapp.Model.RestoModel

class MainRepository {

    val location = listOf("Shah Alam, Selangor","Bangi, Selangor")
    val category = listOf("All","Small Vendor","Dessert","Luxury")

    val exampleText:String = "Vegetarian friendly, Vegan options, Halal"

    val items = listOf(
        RestoModel(
            "Mozer's",
            "logo1",
            "8am-9pm",
            "Mediterranean",
            "Shah Alam, Selangor",
            "RM20-RM40",
            "1",
            exampleText,
            "4.0",
            "Churros,\nHummus with bread,\n Mandy lamb,\n Mixed grill platter,\n Chicken shawarma platter,\n Makluba rice with lamb,\n Makhlouba rice lamb,\n Mozer's salad"

        ),
        RestoModel(
            "Hadramawt Kitchen",
            "logo2",
            "8am-9pm",
            "Mediterranean",
            "Shah Alam, Selangor",
            "RM20-RM40",
            "2",
            exampleText,
            "4.5",
            "Churros,\nHummus with bread,\n Mandy lamb,\n Mixed grill platter,\n Chicken shawarma platter,\n Makluba rice with lamb,\n Makhlouba rice lamb,\n Mozer's salad"


        ),

        RestoModel(
            "Al-Rawsha Restaurant",
            "logo3",
            "8am-9pm",
            "Mediterranean",
            "Shah Alam, Selangor",
            "RM20-RM40",
            "3",
            exampleText,
            "4.3",
            "Churros,\nHummus with bread,\n Mandy lamb,\n Mixed grill platter,\n Chicken shawarma platter,\n Makluba rice with lamb,\n Makhlouba rice lamb,\n Mozer's salad"
        ),

    )
}