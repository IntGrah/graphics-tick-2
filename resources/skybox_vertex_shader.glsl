#version 140

in vec3 oc_position;

out vec3 frag_texcoord;

uniform mat4 mvp_matrix;

void main()
{
    frag_texcoord = oc_position;
    gl_Position = mvp_matrix * vec4(oc_position, 1.0);
}
