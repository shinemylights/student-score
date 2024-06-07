import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: ()=>import('@/views/login/login'),
    hidden: true
  },
  {
    path: '/register',
    component: ()=>import('@/views/login/register'),
    hidden: true
  },
  //个人主页
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
]

export const asyncRoutes = [
  //学生独有
  {
    path: '/',
    component: Layout,
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/home'),
        meta: {
          title: '个人主页',
          icon: '#icon-wxbzhuye',
          roles: ['student']
        }
      },
    ]
  },
  //学生画像
  {
    path: '/portrait',
    component: Layout,
    meta: {
      title: '学生画像',
      icon: '#icon-_xuesheng',
      roles: ['student']
    },
    children: [
      {
        path: 'radar',
        name: 'radar',
        component: () => import('@/views/portrait/RadarChart.vue'),
        meta: {
          title: '雷达图',
          icon: '#icon-xuekepaiming',
          roles: ['student']
        }
      },
      {
        path: 'wordCloud',
        name: 'wordCloud',
        component: () => import('@/views/portrait/WordCloud.vue'),
        meta: {
          title: '词云图',
          icon: '#icon-icon-user',
          roles: ['student']
        }
      },
      {
        path: '/library/LibraryStats',
        name: '/library/LibraryStats',
        component: () => import('@/views/library/LibraryStats'),
        meta: {
          title: '图书馆学习画像',
          icon: '#icon-fenxi',
          roles: ['student']
        }
      },
    ]
  },
  {
    path: '/declare',
    component: Layout,
    meta: {
      title: '综合申报',
      icon: '#icon-zhuangxiangfuhe',
      roles: ['student']
    },
    children: [
      {
        path: '/competition/index',
        name: 'index',
        component: () => import('@/views/competition/index'),
        meta: {
          title: '竞赛获奖',
          icon: '#icon-web__zhuanye',
          roles: ['student']
        }
      },
      {
        path: 'practice',
        name: 'practice',
        component: () => import('@/views/declare/practice/index'),
        meta: {
          title: '社会实践',
          icon: '#icon-xuesheng',
          roles: ['student']
        }
      },
      {
        path: 'skill',
        name: 'skill',
        component: () => import('@/views/declare/skill/index'),
        meta: {
          title: '技能特长',
          icon: '#icon-icon-user',
          roles: ['student']
        }
      },
    ]
  },
  // {
  //   path: '/competition',
  //   component: Layout,
  //   meta: {
  //     title: '竞赛',
  //     icon: '#icon-js',
  //     roles: ['student']
  //   },
  //   children: [
  //     {
  //       path: 'index',
  //       name: 'index',
  //       component: () => import('@/views/competition/index'),
  //       meta: {
  //         title: '竞赛信息',
  //         icon: '#icon-jsinfo',
  //         roles: ['student']
  //       }
  //     }
  //   ]
  // },
  // {
  //   path: '/library',
  //   component: Layout,
  //   meta: {
  //     title: '竞赛',
  //     icon: '#icon-js',
  //     roles: ['student']
  //   },
  //   children: [
  //     {
  //       path: 'LibraryStats',
  //       name: 'LibraryStats',
  //       component: () => import('@/views/library/LibraryStats'),
  //       meta: {
  //         title: '图书馆出入',
  //         icon: '#icon-jsinfo',
  //         roles: ['student']
  //       }
  //     },
  //   ]
  // },
  {
    path: '/score',
    component: Layout,
    meta: {
      title: '成绩查询',
      icon: '#icon-chengji',
      roles: ['student']
    },
    children: [
      {
        path: 'scoreList',
        name: 'scoreList',
        component: () => import('@/views/score/scoreList'),
        meta: {
          title: '个人成绩',
          icon: '#icon-chengjixinxi',
          roles: ['student']
        }
      },
      {
        hidden: true,
        path: 'subjectRank',
        name: 'subjectRank',
        component: () => import('@/views/score/subjectRank'),
        meta: {
          title: '学科排名',
          icon: '#icon-xuekepaiming',
          roles: ['student']
        }
      },
      {
        path: 'gpaRank',
        name: 'gpaRank',
        component: () => import('@/views/score/gpaRank'),
        meta: {
          title: '绩点排名',
          icon: '#icon-jidianguanli',
          roles: ['student']
        }
      },
      {
        path: 'scoreAnalysis',
        name: 'scoreAnalysis',
        component: () => import('@/views/score/scoreAnalysis'),
        meta: {
          title: '分析报告',
          icon: '#icon-fenxi',
          roles: ['student']
        }
      }
    ]
  },
  {
    path: '/WenJuan',
    component: Layout,
    meta: {
      title: '自评问卷',
      icon: '#icon-zizhuxuanke',
      roles: ['student']
    },
    children: [
      {
        path: 'pinDeWenJuan',
        name: 'pinDeWenJuan',
        component: () => import('@/views/WenJuan/pinDeWenJuan/index'),
        meta: {
          title: '品德自评问卷',
          icon: '#icon-kechengxinxi',
          roles: ['student']
        }
      },
      {
        path: 'shenXinWenJuan',
        name: 'shenXinWenJuan',
        component: () => import('@/views/WenJuan/shenXinWenJuan/index'),
        meta: {
          title: '身心自评问卷',
          icon: '#icon-kechengxinxi',
          roles: ['student']
        }
      },
      {
        path: 'xueYeWenJuan',
        name: 'xueYeWenJuan',
        component: () => import('@/views/WenJuan/xueYeWenJuan/index'),
        meta: {
          title: '学业自评问卷',
          icon: '#icon-kechengxinxi',
          roles: ['student']
        }
      }
    ]
  },

  //管理员独有
  {
    path: '/student',
    component: Layout,
    meta: {
      title: '学生管理',
      icon: '#icon-_xuesheng',
      roles: ['admin']
    },
    children: [
      {
        path: 'stuList',
        name: 'stuList',
        component: () => import('@/views/student/stuList'),
        meta: {
          title: '学生信息',
          icon: '#icon-xuesheng',
          roles: ['admin']
        }
      },
      {
        path: 'teaRadar',
        name: 'teaRadar',
        component: () => import('@/views/teaPortrait/RadarChart.vue'),
        meta: {
          title: '雷达图',
          icon: '#icon-xuekepaiming',
          roles: ['admin']
        }
      }
      ]},
  {
    path: '/audit',
    component: Layout,
    meta: {
      title: '申报管理',
      icon: '#icon-sushe',
      roles: ['admin']
    },
    children: [
      {
        path: '/competition/auditList',
        name: 'auditList',
        component: () => import('@/views/audit/competition/auditList.vue'),
        meta: {
          title: '奖项审核',
          icon: '#icon-zhuangxiangfuhe',
          roles: ['teacher','admin']
        }
      },
      {
        hidden: true,
        path: '/competition/auditDetail',
        name: 'compauditDetail',
        component: () => import('@/views/audit/competition/auditDetail.vue'),
        meta: {
          title: '奖项复核详情',
          roles: ['teacher','admin','student']
        }
      },
      {
        path: '/practice/auditList',
        name: 'auditList',
        component: () => import('@/views/audit/practice/auditList.vue'),
        meta: {
          title: '社会实践审核',
          icon: '#icon-zhuangxiangfuhe',
          roles: ['teacher','admin']
        }
      },
      {
        hidden: true,
        path: '/practice/auditDetail',
        name: 'pracauditDetail',
        component: () => import('@/views/audit/practice/auditDetail.vue'),
        meta: {
          title: '社会实践详情',
          roles: ['teacher','admin','student']
        }
      },
      {
        path: '/skill/auditList',
        name: 'auditList',
        component: () => import('@/views/audit/skill/auditList.vue'),
        meta: {
          title: '技能特长审核',
          icon: '#icon-zhuangxiangfuhe',
          roles: ['teacher','admin']
        }
      },
      {
        hidden: true,
        path: '/skill/auditDetail',
        name: 'skillauditDetail',
        component: () => import('@/views/audit/skill/auditDetail.vue'),
        meta: {
          title: '技能特长详情',
          roles: ['teacher','admin','student']
        }
      }
    ]
  },
  {
    path: '/tag',
    name: 'tag',
    component: Layout,
    meta: {
      title: '标签管理',
      icon: '#icon-sushe',
      roles: ['admin']
    },
    children: [
      {
        path: 'tagList',
        name: 'tagList',
        component: () => import('@/views/tag/tagList'),
        meta: {
          title: '标签管理',
          icon: '#icon-xuesheng',
          roles: ['admin']
        }
      }
      // {
      //   path: 'couAdd',
      //   name: 'couAdd',
      //   component: () => import('@/views/course/couAdd'),
      //   meta: {
      //     title: '绑定课程',
      //     icon: '#icon-zizhuxuanke',
      //     roles: ['teacher','admin']
      //   }
      // }
    ]
  },
      {
        path: '/WenJuan',
        component: Layout,
        meta: {
          title: '问卷得分',
          icon: '#icon-_xuesheng',
          roles: ['admin']
        },
        children: [
          {
            path: 'wenJuanScores',
            name: 'stuList',
            component: () => import('@/views/WenJuan/wenJuanScores'),
            meta: {
              title: '问卷得分',
              icon: '#icon-xuesheng',
              roles: ['admin']
            }
          }]},
      // {
      //   path: 'dormList',
      //   name: 'dormList',
      //   component: () => import('@/views/student/dormList'),
      //   meta: {
      //     title: '宿舍信息',
      //     icon: '#icon-sushe',
      //     roles: ['admin']
      //   }
      // }
  {
    path: '/teacher',
    component: Layout,
    children: [
      {
        path: 'teacherList',
        name: 'teacherList',
        component: () => import('@/views/teacher/teacherList'),
        meta: {
          title: '教师信息',
          icon: '#icon-wxbzhuye',
          roles: ['admin']
        }
      },
    ]
  },
  {
    path: '/course',
    component: Layout,
    children: [
      {
        hidden: true,
        path: 'roomList',
        name: 'roomList',
        component: () => import('@/views/classRoom/roomAdd'),
        meta: {
          title: '课堂列表',
          icon: '#icon-ketang',
          roles: ['admin','student']
        }
      },
    ]
  },
  {
    path: '/majorCourse',
    component: Layout,
    children: [
      {
        path: 'majorCourseList',
        name: 'majorCourseList',
        component: () => import('@/views/majorCourse/majorCourseList'),
        meta: {
          title: '专业课程信息',
          icon: '#icon-web__zhuanye',
          roles: ['admin']
        }
      },
    ]
  },
  // {
  //   path: '/bingCourse',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'bingCourse',
  //       name: 'bingCourse',
  //       component: () => import('@/views/course/bingCourse'),
  //       meta: {
  //         title: '选课管理',
  //         icon: '#icon-zizhuxuanke',
  //         roles: ['admin']
  //       }
  //     },
  //   ]
  // },

  // 教师独有
  {
    path: '/courseBind',
    component: Layout,
    meta: {
      title: '绑定课程',
      icon: '#icon-kechengxinxi',
      roles: ['teacher']
    },
    children: [
      {
        path: 'couAdd',
        name: 'couAdd',
        component: () => import('@/views/course/couAdd'),
        meta: {
          title: '绑定课程',
          icon: '#icon-zizhuxuanke',
          roles: ['teacher']
        }
      }
    ]
  },
  // 成绩复核
  // 课堂管理
  // {
  //   path: '/classRoom',
  //   component: Layout,
  //   meta: {
  //     title: '课堂管理',
  //     icon: '#icon-ketang2',
  //     roles: ['student','teacher']
  //   },
  //   children: [
  //     {
  //       path: 'roomListTea',
  //       name: 'roomListTea',
  //       component: () => import('@/views/classRoom/roomListTea'),
  //       meta: {
  //         title: '我的课堂',
  //         icon: '#icon-ketang1',
  //         roles: ['teacher']
  //       }
  //     },
  //     {
  //       path: 'roomListStu',
  //       name: 'roomListStu',
  //       component: () => import('@/views/classRoom/roomListStu'),
  //       meta: {
  //         title: '我的课堂',
  //         icon: '#icon-ketang1',
  //         roles: ['student']
  //       }
  //     },
  //     {
  //       hidden:true,
  //       path: 'roomDetail',
  //       name: 'roomDetail',
  //       component: () => import('@/views/classRoom/roomDetail'),
  //       meta: {
  //         roles: ['teacher','student']
  //       }
  //     },
  //     {
  //       path: 'roomAdd',
  //       name: 'roomAdd',
  //       component: () => import('@/views/course/bingCourse'),
  //       meta: {
  //         title: '加入课堂',
  //         icon: '#icon-ketang',
  //         roles: ['student']
  //       }
  //     }
  //   ]
  // },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router

