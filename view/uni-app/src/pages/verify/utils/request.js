import signUtil from './signUtil.js';

let baseUrl = "https://mirror.anji-plus.com/api"

export const myRequest = (option={})=>{
	return new Promise((reslove,reject)=>{
		uni.request({
			url: baseUrl + option.url, 
			data :signUtil.sign(option.data),
			method:option.method || "GET",
			success: (result) => {
				reslove(result)
			},
			fail:(error)=>{
				reject(error)
			}
		})
	})
}
