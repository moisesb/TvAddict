package com.moisesborges.tvaddict.rules;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.robolectric.RuntimeEnvironment;

/**
 * Created by Moisés on 22/04/2017.
 */

public class DBFlow implements TestRule {

    public static DBFlow create() {
        return new DBFlow();
    }

    private DBFlow() {
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                FlowManager.init(RuntimeEnvironment.application);
                try {
                    base.evaluate();
                } finally {
                    FlowManager.destroy();
                }
            }
        };
    }
}