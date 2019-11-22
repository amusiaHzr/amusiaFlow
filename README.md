# amusiaFlow
一个简单的流式布局：

支持自定义布局

![image](https://github.com/amusiaHzr/amusiaFlow/blob/master/flow.jpg)
	
	
![image](https://github.com/amusiaHzr/amusiaFlow/blob/master/flow.gif)


	如何使用：

	  step 1：
	  allprojects {
	      repositories {
		...
		maven { url 'https://jitpack.io' }
	      }
	    }

	  step 2：
	  dependencies {
			implementation 'com.github.amusiaHzr:amusiaFlow:1.0'
		}

	  step 3：
	  //使用简单适配器传入布局文件，数据集合，以及实现convert方法自定义布局
	  //simpleFlowAdapter.setOnItemListener() 设置item的点击和长按事件
	  //也可以重写一个adapter继承BaseFlowAdapter
	  //具体用法项目中有示例。。。
	  flowView = findViewById(R.id.flow_view);
		simpleFlowAdapter = new SimpleFlowAdapter<DataBean>(this, R.layout.item, datas) {
		    @Override
		    public void convert(View itemView, Object object, int position) {
			DataBean dataBean = (DataBean) object;
			TextView nameText = itemView.findViewById(R.id.name);
			ImageView logoImage = itemView.findViewById(R.id.logo);
			nameText.setText(dataBean.getName());
			logoImage.setBackgroundResource(dataBean.getLogo());

		    }
		};

	  step 4:
	  //设置适配器
	  flowView.setAdapter(simpleFlowAdapter);

关于我：
第一次开源项目的三流菜鸡android开发，这个项目比较简单，如果感兴趣的同学可以打开源码看看，每一行基本都有注释


感谢 
享学课堂： alvin老师
网易微专业：熊翔老师


