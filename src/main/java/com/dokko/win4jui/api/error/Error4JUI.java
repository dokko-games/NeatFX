package com.dokko.win4jui.api.error;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.api.Logger4JUI;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error4JUI extends RuntimeException {
    private String name;
    private String description;
    private ErrorData data;
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ErrorData {
        private int cause; // 0 user 1 program 2 known bug 3 unknown
        private int level; // the higher, the more important
        private int identifier; // for similar errors
        private boolean exit;
        private int getExitCode() {
            return -level;
        }
    }
    @Override
    public void printStackTrace() {
        Logger4JUI.error("%{0} thrown: %{1}.", getName(), getDescription());
        Logger4JUI.errorNoCount("Error Cause: %{0}", getData().getCause());
        Logger4JUI.errorNoCount("Error Level: %{0}", getData().getLevel());
        Logger4JUI.errorNoCount("Error ID: %{0}", getData().getIdentifier());
        if(getData().exit) Win4JUI.exit(getData().getExitCode());
    }
}
