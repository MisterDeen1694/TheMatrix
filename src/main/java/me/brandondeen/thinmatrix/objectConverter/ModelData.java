package me.brandondeen.thinmatrix.objectConverter;

public class ModelData {

    private float[] vertices;
    private float[] textureCords;
    private float[] normals;
    private int[] indices;
    private float furthestPoint;

    public ModelData(float[] vertices, float[] textureCords, float[] normals, int[] indices, float furthestPoint) {
        this.vertices = vertices;
        this.textureCords = textureCords;
        this.normals = normals;
        this.indices = indices;
        this.furthestPoint = furthestPoint;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTextureCords() {
        return textureCords;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getIndices() {
        return indices;
    }

    public float getFurthestPoint() {
        return furthestPoint;
    }
}
