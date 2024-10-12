package uk.ac.cam.cl.gfxintro.jc2483.tick2;

/**
 * Class inheriting from Mesh class
 * Defines a Cube mesh by overloading Mesh's 3D position, UV texture coordiantes and normals
 *
 */
public class CubeMesh extends Mesh {
    
	public CubeMesh() {
		super();
		initialize();
	}

	@Override
	float[] initializeVertexPositions() {
        return new float[] {
				-1,  1, -1, -1,  1,  1,  1,  1, -1,
				-1,  1,  1,  1,  1,  1,  1,  1, -1,
				-1, -1,  1, -1, -1, -1,  1, -1,  1,
				-1, -1, -1,  1, -1, -1,  1, -1,  1,
				-1,  1,  1, -1, -1,  1,  1,  1,  1,
				-1, -1,  1,  1, -1,  1,  1,  1,  1,
				-1, -1, -1, -1,  1, -1,  1, -1, -1,
				-1,  1, -1,  1,  1, -1,  1, -1, -1,
				-1, -1, -1, -1, -1,  1, -1,  1, -1,
				-1, -1,  1, -1,  1,  1, -1,  1, -1,
				1,  1, -1,  1,  1,  1,  1, -1, -1,
				1,  1,  1,  1, -1,  1,  1, -1, -1
		};
	}

	@Override
	int[] initializeVertexIndices() {
        return new int[] {
				0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
				24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35
		};
	}

	@Override
	float[] initializeVertexNormals() {
        return new float[] {
				0,  1,  0,  0,  1,  0,  0,  1,  0,
				0,  1,  0,  0,  1,  0,  0,  1,  0,
				0, -1,  0,  0, -1,  0,  0, -1,  0,
				0, -1,  0,  0, -1,  0,  0, -1,  0,
				0,  0,  1,  0,  0,  1,  0,  0,  1,
				0,  0,  1,  0,  0,  1,  0,  0,  1,
				0,  0, -1,  0,  0, -1,  0,  0, -1,
				0,  0, -1,  0,  0, -1,  0,  0, -1,
				-1,  0,  0, -1,  0,  0, -1,  0,  0,
				-1,  0,  0, -1,  0,  0, -1,  0,  0,
				1,  0,  0,  1,  0,  0,  1,  0,  0,
				1,  0,  0,  1,  0,  0,  1,  0,  0
		};
	}

	@Override
	float[] initializeTextureCoordinates() {
        return new float[] {
				1/4f,0, 	1/4f,1/3f, 	1/2f,0,
				1/4f,1/3f,	1/2f,1/3f,	1/2f,0,
				1/4f,2/3f,	1/4f,1,		1/2f,2/3f,
				1/4f,1,		1/2f,1,		1/2f,2/3f,
				1/4f,1/3f, 	1/4f,2/3f,	1/2f,1/3f,
				1/4f,2/3f,	1/2f,2/3f,	1/2f,1/3f,
				1,2/3f,		1,1/3f, 	3/4f,2/3f,
				1,1/3f,		3/4f,1/3f,	3/4f,2/3f,
				0,2/3f,		1/4f,2/3f,	0f,1/3f,
				1/4f,2/3f,	1/4f,1/3f,	0f,1/3f,
				3/4f,1/3f,	1/2f,1/3f,	3/4f,2/3f,
				1/2f,1/3f,	1/2f,2/3f,	3/4f,2/3f
		};
	}
}
