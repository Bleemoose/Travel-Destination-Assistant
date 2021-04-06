package OOPII_21999_219148_219160;

import java.util.ArrayList;

class EmptyArrayList extends Exception{
    EmptyArrayList(String s){
        super(s);
    }
}




abstract class Traveller {
    private int[] termsRating;
    private float[] currentLocation;
    private float p;
    //constructor
    public Traveller(int[] termsRating, float[] currentLocation) {
        this.termsRating = termsRating;
        this.currentLocation = currentLocation;
    }

    //setters getters
    public int[] getTermsRating() {
        return termsRating;
    }

    public void setTermsRating(int[] termsRating) {
        this.termsRating = termsRating;
    }

    public float[] getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(float[] currentLocation) {
        this.currentLocation = currentLocation;
    }


    public abstract double calculate_terms_similarity(int [] userTerms, int [] cityTerms);

    public float getP() {
        return p;
    }

    public void setP(float p) {
        this.p = p;
    }


    /*
        geodesic_vector[0] is longitude
        geodesic_vector[1] is latitude
        */
    //returns distance in KMs
    public  double calculate_distance(City targetCity) {
        if ((this.currentLocation[1] == targetCity.getGeodesic_vector()[1]) && (this.currentLocation[0] == targetCity.getGeodesic_vector()[0])) {
            return 0;
        }
        else {
            double theta = this.currentLocation[0] - targetCity.getGeodesic_vector()[0];
            double dist = Math.sin(Math.toRadians(this.currentLocation[1])) * Math.sin(Math.toRadians(targetCity.getGeodesic_vector()[1])) + Math.cos(Math.toRadians(this.currentLocation[1])) * Math.cos(Math.toRadians(targetCity.getGeodesic_vector()[1])) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344; // convert to kilometres
            return (dist);
        }
    }

    // Function to calculate the
    // log base 2 of an integer
    //https://www.geeksforgeeks.org/how-to-calculate-log-base-2-of-an-integer-in-java/
    public static double log2(double N)
    {
        // calculate log2 N indirectly
        // using log() method

        return (Math.log(N) / Math.log(2));
    }


    public double similarity_geodesic_vector(City targetCity){
        int maxDist =  15317 ; //https://www.distance.to/Athens/Sydney
        return log2(2/(2-calculate_distance(targetCity)/maxDist));
    }



    public double calculate_similarity(City targetCity){
        return p*calculate_terms_similarity(termsRating,targetCity.getTerms_vector()) + (1-p)* similarity_geodesic_vector(targetCity);
    }

    public City compare_cities(ArrayList<City> cityArrayList) {
        double max = -1f;
        int loc = 0;
        try {
            loc = 0;
            max = calculate_similarity(cityArrayList.get(0));
        }catch (Exception e){
            System.out.println("ArrayList is empty");
            return null;
        }
       for (int i = 1;i < cityArrayList.size();i++){
           if (calculate_similarity(cityArrayList.get(i)) > max){
                loc = i;
                max = calculate_similarity(cityArrayList.get(i));
           }
       }
        return cityArrayList.get(loc);
    }
    public  City[] compare_cities(ArrayList<City> cityArrayList,int amount){
        if (cityArrayList.size() < 2){
            System.out.println("Invalid arraylist size");
        }
        City[] returnArr = new City[amount];
        double[] similarityArr = new double[cityArrayList.size()];
        int[] locArr = new int[cityArrayList.size()];
        for (int i = 0; i < cityArrayList.size();i++){
            similarityArr[i] = calculate_similarity(cityArrayList.get(i));
            locArr[i] = i;
        }
        int n = cityArrayList.size();
        double temp;
        int tmp2;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(similarityArr[j-1] < similarityArr[j]){
                    //swap elements
                    temp = similarityArr[j-1];
                    similarityArr[j-1] = similarityArr[j];
                    similarityArr[j] = temp;
                    tmp2 = locArr[j-1];
                    locArr[j-1] = locArr[j];
                    locArr[j] = tmp2;
                }

            }
        }
        for (int i =0 ; i < amount;i++){
            returnArr[i] = cityArrayList.get(locArr[i]);
        }
        return returnArr;
    }
}
