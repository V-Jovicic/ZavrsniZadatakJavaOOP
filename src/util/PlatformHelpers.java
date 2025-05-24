package util;

import models.users.User;

public class PlatformHelpers {

    // TODO
    public static void userLoggedInMenu(User activeUser) {
        if(activeUser.getType().equalsIgnoreCase("iznajmljivac")) {
            System.out.println("");
        }
    }
}
