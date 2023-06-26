const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  //avatar: state => state.user.user.avatar,
  //name: state => state.user.user.name,
 //roles: state => state.user.user.roles,
  permission_routes: state => state.permission.routes,
}
export default getters
