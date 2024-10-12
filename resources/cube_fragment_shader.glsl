#version 140

in vec3 wc_frag_normal;
in vec2 frag_texcoord;
in vec3 wc_frag_pos;

out vec3 color;

uniform sampler2D tex;
uniform samplerCube skybox;
uniform vec3 wc_camera_position;

vec3 tonemap(vec3 linearRGB)
{
    float L_white = 0.7;

    float inverseGamma = 1./2.2;
    return pow(linearRGB/L_white, vec3(inverseGamma));
}

void main()
{
    vec3 wc_light_pos = vec3(-1, 5.0, -1);
    vec3 I = vec3(0.941, 0.968, 1);
    vec3 I_a = vec3(0.16, 0.0, 0.0);
    float k_d = 0.4;
    float k_s = 0.75;
    vec3 C_diff = texture(tex, frag_texcoord).xyz;
    vec3 C_spec = vec3(1.0, 1.0, 1.0);
    float alpha = 32.0;

    vec3 L = normalize(wc_light_pos - wc_frag_pos);
    vec3 N = normalize(wc_frag_normal);
    vec3 R = reflect(L, N);
    vec3 V = normalize(wc_camera_position - wc_frag_pos);
    vec3 B = wc_frag_pos + reflect(V, N);
//    vec3 B = reflect(-V, N);

    vec3 ambient = C_diff * I_a;
    vec3 diffuse = C_diff * k_d * I * max(0, dot(L, N));
    vec3 specular = C_spec * k_s * I * pow(max(0, dot(R, V)), alpha);
    vec3 reflection = 0.2 * texture(skybox, B).xyz;

    vec3 linear_color = ambient + diffuse + specular + reflection;

	color = tonemap(linear_color);
}
