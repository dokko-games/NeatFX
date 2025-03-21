package com.dokko.win4jui.api.design;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.swing.*;
@Getter
@Accessors(chain = true)
@Setter
@AllArgsConstructor
public class Design4JUI {
    private String name;
    private boolean dark;
    private Decoration decoration;
    private LookAndFeel lookAndFeel;

    public boolean isDecorated() {
        return !(decoration == Decoration.NONE || decoration == Decoration.DEFAULT);
    }
}
