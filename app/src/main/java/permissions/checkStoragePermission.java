package permissions;

import android.content.pm.PackageManager;

import downloadLectio.GetGyms;

public class checkStoragePermission {

    static final int PERMISSION_STORAGE = 0;


    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        while (true) {
            switch (requestCode) {
                case PERMISSION_STORAGE: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //new GetGyms().execute();
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.

                        break;

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }
            System.out.println("i tried");
        }

    }
}