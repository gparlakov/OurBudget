package com.parlakovi.petqjoro.ourbudget;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by gparl_000 on 6/23/2015.
 */
public class OptionsMenuHandler {
    private CharSequence[] Ids;
    private Method[] Methods;

    final public void Init(CharSequence[] ids,  Method[] methodHandler){
        this.Ids = ids;
        this.Methods = methodHandler;
    };

    public void onClick(CharSequence id, Object... argsss){
        for (int i = 0; i < this.Ids.size(); i++) {
            if (id.equals(this.Ids[i])){
                Method method = this.Methods[i];
                method.invoke(this, argsss);
            }
        }
    }
}
