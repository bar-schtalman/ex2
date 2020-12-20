package api;

public class geoLocation implements geo_location{
    private double x;
    private double y;
    private double z;
    public geoLocation(){
        this.x=0;
        this.y=0;
        this.z=0;
    }
    public geoLocation(double x1, double y1, double z1){
        this.x=x1;
        this.y=y1;
        this.z=z1;
    }
    public geoLocation(double x1, double y1){
        this(x1,y1,0);
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(geo_location g) {
        return (Math.pow(this.x-g.x(),2)+Math.pow(this.y-g.y(),2)+Math.pow(this.z-g.z(),2));
    }
}
