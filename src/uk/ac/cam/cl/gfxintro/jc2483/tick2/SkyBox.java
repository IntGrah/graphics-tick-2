package uk.ac.cam.cl.gfxintro.jc2483.tick2;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class SkyBox {
    private final static String VSHADER_FN = "resources/skybox_vertex_shader.glsl";
    private final static String FSHADER_FN = "resources/skybox_fragment_shader.glsl";

	private final Mesh skybox_mesh = new CubeMesh();
	private final ShaderProgram skybox_shader;
	private final Texture skybox_texture = new Texture();

	public SkyBox() {
		skybox_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		skybox_shader.bindDataToShader("oc_position", skybox_mesh.vertex_handle, 3);
		String[] filenames = {
				"resources/skybox/right.png",
				"resources/skybox/left.png",
				"resources/skybox/top.png",
				"resources/skybox/bottom.png",
				"resources/skybox/front.png",
				"resources/skybox/back.png"
		};
		skybox_texture.loadCubemap(filenames);
	}
	

	/**
	 * Updates the scene and then renders the CubeRobot
	 * @param camera - Camera to be used for rendering
	 * @param deltaTime		- Time taken to render this frame in seconds (= 0 when the application is paused)
	 * @param elapsedTime	- Time elapsed since the beginning of this program in millisecs
	 */
	public void render(Camera camera, float deltaTime, long elapsedTime) {
		GL11.glDisable(GL_CULL_FACE);
		GL11.glDepthMask(false);

		renderMesh(camera, skybox_mesh, new Matrix4f().scale(new Vector3f(10f,10f,10f)), skybox_shader, skybox_texture);

		GL11.glDepthMask(true);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}
	
	/**
	 * Draw mesh from a camera perspective
	 * @param camera		- Camera to be used for rendering
	 * @param mesh			- mesh to render
	 * @param modelMatrix	- model transformation matrix of this mesh
	 * @param shader		- shader to colour this mesh
	 * @param texture		- texture image to be used by the shader
	 */
	public void renderMesh(Camera camera, Mesh mesh , Matrix4f modelMatrix, ShaderProgram shader, Texture texture) {
		shader.reloadIfNeeded(); 
		shader.useProgram();

		Matrix4f view_matrix = camera.getViewMatrix();
		view_matrix.setTranslation(new Vector3f(0,0,0));
		Matrix4f mvp_matrix = new Matrix4f(camera.getProjectionMatrix()).mul(view_matrix).mul(modelMatrix);;
		shader.uploadMatrix4f(mvp_matrix, "mvp_matrix");

		bindCubemap();
		shader.bindTextureToShader("skybox", 1);

		glBindVertexArray(mesh.vertexArrayObj);
		glDrawElements(GL_TRIANGLES, mesh.no_of_triangles, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);

		unBindCubemap();
	}
	
	/**
	 * Binds skybox texture to active texture units
	 * */
	public void bindCubemap() {
		skybox_texture.bindCubemap();
	}
	
	/**
	 * Unbinds skybox texture
	 * */
	public void unBindCubemap() {
		skybox_texture.unBindCubemap();
	}
}
