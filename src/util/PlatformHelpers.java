package util;

import models.users.User;

public class PlatformHelpers {

    // WORK ON
    public static void userLoggedInDisplay(User activeUser) {
        if(activeUser.getType().equalsIgnoreCase("iznajmljivac")) {
            System.out.println("");
        }
    }
}
