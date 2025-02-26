#version 140

in vec3 oc_position;
in vec3 oc_normal;
in vec2 texcoord;

out vec3 wc_frag_normal;
out vec2 frag_texcoord;
out vec3 wc_frag_pos;

uniform mat4 mvp_matrix;
uniform mat4 m_matrix;
uniform mat3 normal_matrix;

void main()
{
    frag_texcoord = texcoord;

    wc_frag_normal = normal_matrix * normalize(oc_normal);
    wc_frag_pos = vec3(m_matrix * vec4(oc_position, 1.0));
    gl_Position = mvp_matrix * vec4(oc_position, 1.0);
}
