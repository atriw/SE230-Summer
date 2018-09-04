export default class Request {
    static get(url) {
        const baseurl = 'http://10.0.0.2:8080'
        return fetch(baseurl + url).then((res) => res.json())
    }

    static post(url, body) {
        const baseurl = 'http://10.0.0.2:8080'
        return fetch(baseurl + url, {
            method: 'POST',
            body: JSON.stringify(body)
        }).then((res) => res.json())
    }
}