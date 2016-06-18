package com.spreys.motorbikedirectory;

import android.app.Application;

import com.spreys.motorbikedirectory.Model.Make;

import java.util.List;

/**
 * Created by vspreys on 18/06/16.
 */

public class MotorbikesApplication extends Application {
    private List<Make> makes;

    void setMakes(List<Make> makes) {
        this.makes = makes;
    }

    public List<Make> getMakes(){
        return this.makes;
    }
}
