import axiosClient from "./axiosClient";

const articleApi = {
    getAll: (params) => {
        const url = '/articles';

        return axiosClient.get(url, {params});
    },

    post: (data) => {
        const url = '/articles';

        return axiosClient.post(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        })
    }
};

export default articleApi;