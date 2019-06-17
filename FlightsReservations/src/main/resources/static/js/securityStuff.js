export function checkRoleFromToken(token, role) {
    var roles = parseJwt(token).roles;
    if(!checkIfRolesContainsAuthority(roles, role)) {
        return false;
    }
    return true;
}

export function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = decodeURIComponent(atob(base64Url).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(base64);
};

export function checkIfRolesContainsAuthority(roles, authority) {
    for (var r of roles) {
        if (r.authority == authority) return true;
    }
    return false;
}