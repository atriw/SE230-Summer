
export default {
    'POST /login/checklogin': (req, res) => {
        const username = req.username;
        const password = req.password;
        if (username ==='user' && password === 'user' ) {
          res.send({
            status: 'ok',
            currentAuthority: 'user',
          });
          return;
        }
        if (password === '123456' && userName === 'user') {
          res.send({
            status: 'ok',
            currentAuthority: 'user',
          });
          return;
        }
        res.send({
          status: 'ok',
          username: req.body,
          currentAuthority: 'guest',
        });
      },
};
