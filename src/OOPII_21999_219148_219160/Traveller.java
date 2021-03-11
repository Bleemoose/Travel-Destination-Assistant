package OOPII_21999_219148_219160;

abstract class Traveller {
    private int[][] termsRating;
    private float[] currentLocation;
    //constructor
    public Traveller(int[][] termsRating, float[] currentLocation) {
        this.termsRating = termsRating;
        this.currentLocation = currentLocation;
    }

    //setters getters
    public int[][] getTermsRating() {
        return termsRating;
    }

    public void setTermsRating(int[][] termsRating) {
        this.termsRating = termsRating;
    }

    public float[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(float[] currentLocation) {
        this.currentLocation = currentLocation;
    }
}