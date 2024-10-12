package uk.ac.cam.cl.gfxintro.jc2483.tick2;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class CubeRobot {
    private final static String VSHADER_FN = "resources/cube_vertex_shader.glsl";
    private final static String FSHADER_FN = "resources/cube_fragment_shader.glsl";

    public SkyBox skybox = new SkyBox();

	private final Mesh mesh = new CubeMesh();
	private final ShaderProgram shader;
	private final Texture body_texture = new Texture();
	private final Texture head_texture = new Texture();

	public CubeRobot() {
		shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		shader.bindDataToShader("oc_position", mesh.vertex_handle, 3);
		shader.bindDataToShader("oc_normal", mesh.normal_handle, 3);
		shader.bindDataToShader("texcoord", mesh.tex_handle, 2);
		body_texture.load("resources/cubemap.png");
		head_texture.load("resources/cubemap_head.png");
	}

	public void render(Camera camera, float deltaTime, long elapsedTime) {
		Matrix4f body_transform = new Matrix4f()
				.scale(0.75f, 1.5f, 0.75f)
				.rotateAffineXYZ(0.0f, -elapsedTime / 400f, 0.0f);

		float arm_angle = 0.5f + 0.3f * (float) Math.sin(elapsedTime / 200f);

		Matrix4f head_transform = new Matrix4f()
				.rotateAffineXYZ(0.0f, -elapsedTime / 400f, 0.0f)
				.translate(0.0f, 2.0f, 0.0f)
				.scale(0.5f, 0.5f, 0.5f);

		Matrix4f right_arm_transform = new Matrix4f()
				.rotateAffineXYZ(0.0f, -elapsedTime / 400f, 0.0f)
				.translate(1.0f, -0.25f, 0.125f)
				.translate(0.0f, 1.0f, 0.0f)
				.rotateAffineXYZ(0.0f, 0.0f, arm_angle)
				.translate(0.0f, -1.0f, 0.0f)
				.scale(0.25f, 1.25f, 0.25f);

		Matrix4f left_arm_transform = new Matrix4f()
				.rotateAffineXYZ(0.0f, -elapsedTime / 400f, 0.0f)
				.translate(-1.0f, -0.25f, 0.125f)
				.translate(0.0f, 1.0f, 0.0f)
				.rotateAffineXYZ(0.0f, 0.0f, -arm_angle)
				.translate(0.0f, -1.0f, 0.0f)
				.scale(0.25f, 1.25f, 0.25f);

		float leg_angle = 0.5f * (float) Math.sin(elapsedTime / 200f);

		Matrix4f right_leg_transform = new Matrix4f()
				.rotateAffineXYZ(0.0f, -elapsedTime / 400f, 0.0f)
				.translate(0.5f, -2.0f, 0.0f)
				.translate(0.0f, 0.7f, 0.0f)
				.rotateAffineXYZ(leg_angle, 0.0f, 0.0f)
				.translate(0.0f, -0.7f, 0.0f)
				.scale(0.15f, 0.75f, 0.15f);

		Matrix4f left_leg_transform = new Matrix4f();
		left_leg_transform
				.rotateAffineXYZ(0.0f, -elapsedTime / 400f, 0.0f)
				.translate(-0.5f, -2.0f, 0.0f)
				.translate(0.0f, 0.7f, 0.0f)
				.rotateAffineXYZ(-leg_angle, 0.0f, 0.0f)
				.translate(0.0f, -0.7f, 0.0f)
				.scale(0.15f, 0.75f, 0.15f);

		renderMesh(camera, mesh, head_transform, shader, head_texture);
		renderMesh(camera, mesh, body_transform, shader, body_texture);
		renderMesh(camera, mesh, right_arm_transform, shader, body_texture);
		renderMesh(camera, mesh, left_arm_transform, shader, body_texture);
		renderMesh(camera, mesh, right_leg_transform, shader, body_texture);
		renderMesh(camera, mesh, left_leg_transform, shader, body_texture);
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

		Matrix4f mvp_matrix = new Matrix4f(camera.getProjectionMatrix()).mul(camera.getViewMatrix()).mul(modelMatrix);
		Matrix3f normal_matrix = new Matrix3f();
		mvp_matrix.normal(normal_matrix);

		shader.uploadMatrix4f(mvp_matrix, "mvp_matrix");
		shader.uploadMatrix4f(modelMatrix, "m_matrix");
		shader.uploadVector3f(camera.getCameraPosition(), "wc_camera_position");
		shader.uploadMatrix3f(normal_matrix, "normal_matrix");

		texture.bindTexture();
		shader.bindTextureToShader("tex", 0);
		skybox.bindCubemap();
		shader.bindTextureToShader("skybox", 1);

		glBindVertexArray(mesh.vertexArrayObj);
		glDrawElements(GL_TRIANGLES, mesh.no_of_triangles, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);

		texture.unBindTexture();
		skybox.unBindCubemap();
	}
}
