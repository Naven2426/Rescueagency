package com.example.rescueagency.admin.spinner;

import com.example.rescueagency.R;

import java.util.ArrayList;
import java.util.List;

public class Data {

    public static List<Fruit> getFruitList() {
        List<Fruit> fruitList = new ArrayList<>();

        Fruit Avocado = new Fruit();
        Avocado.setName("Avocado");
        Avocado.setImage(R.mipmap.info);
        fruitList.add(Avocado);

        Fruit Banana = new Fruit();
        Banana.setName("Banana");
        Banana.setImage(R.mipmap.info);
        fruitList.add(Banana);

        Fruit Coconut = new Fruit();
        Coconut.setName("Coconut");
        Coconut.setImage(R.mipmap.info);
        fruitList.add(Coconut);

        Fruit Guava = new Fruit();
        Guava.setName("Guava");
        Guava.setImage(R.mipmap.info);
        fruitList.add(Guava);

        Fruit Lemon = new Fruit();
        Lemon.setName("Lemon");
        Lemon.setImage(R.mipmap.info);
        fruitList.add(Lemon);

        Fruit Mango = new Fruit();
        Mango.setName("Mango");
        Mango.setImage(R.mipmap.info);
        fruitList.add(Mango);

        Fruit Orange = new Fruit();
        Orange.setName("Orange");
        Orange.setImage(R.mipmap.info);
        fruitList.add(Orange);

        return fruitList;
    }

}
