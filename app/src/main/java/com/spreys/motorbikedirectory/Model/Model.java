package com.spreys.motorbikedirectory.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vspreys on 18/06/16.
 */

public class Model {
    private String name;
    private String modelClass;
    private int engineSize;

    public Model(String name, String modelClass, int engineSize) {
        this.name = name;
        this.modelClass = modelClass;
        this.engineSize = engineSize;
    }
}
