import axios from 'axios'
export default class Request {
    static get(url) {
        const baseurl = 'http://10.0.2.2:8080'
        return axios.get(baseurl + url)
    }

    static post(url, body) {
        const baseurl = 'http://10.0.2.2:8080'
        return axios.post(baseurl + url, body)
    }
}