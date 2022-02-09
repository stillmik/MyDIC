package com.demo.ioc.aa_Part2.testClasses;

import com.demo.ioc.aa_Part2.ResolvePackich;
import com.demo.ioc.aa_Part2.tree.TreeNode;

import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CycleRegistrator {

    private int MAX_CYCLES;
    private int currentCycle;
    private Class<?> cycleClass;


    public CycleRegistrator(Class<?> cycleClass, int maxCycles) {
        if (maxCycles <= 1) {
            MAX_CYCLES = 2;
        } else MAX_CYCLES = Math.min(maxCycles, 10);
        this.cycleClass = cycleClass;
        currentCycle = 0;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public void incCurrentCycle() {
        currentCycle++;
    }

    public boolean isReached() {
        return currentCycle == MAX_CYCLES;
    }

    public Class<?> getCycleClass() {
        return cycleClass;
    }
}
