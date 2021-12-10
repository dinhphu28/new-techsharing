import axiosClient from "./axiosClient";

const usersRoleApi = {
    getAll: (params) => {
        const url = '/users';

        return axiosClient.get(url, {params})
    },

    putForceChangePassword: (data) => {
        const url = '/users/password';

        return axiosClient.put(url, data);
    },

    putChangeActiveState: (data) => {
        const url = '/users/active-state';

        return axiosClient.put(url, data);
    },

    post: (data) => {
        const url = '/users';

        return axiosClient.post(url, data);
    }
}

export default usersRoleApi;