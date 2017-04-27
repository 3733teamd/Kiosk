package com.cs3733.teamd.Controller;

/**
 * Created by sdmichelini on 4/27/17.
 */
public interface IOperationListener {
    void onOperationSuccess(Object o);
    void onOperationFailure(Object o);
}
