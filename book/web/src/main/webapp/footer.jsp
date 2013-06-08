<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<footer class="g-ft">
	<div class="m-cprt">
		Copyright&copy;Retechcorp.com <a href="http://www.miibeian.gov.cn/"
			target="_blank" hidefocus="hidefocus">京ICP备XXX号</a> <a
			href="http://www.gapp.gov.cn/" target="_blank" hidefocus="hidefocus">电复证字第XXX号</a>
	</div>
</footer>
<script type="text/javascript" src="/static/lib/seajs/2.0.0/sea.js"></script>
<script type="text/javascript">
	seajs.config({
		 // Enable plugins
		  plugins: ['shim'],

		  // Configure alias
		  alias: {
		    'jquery': {
		      src: '/static/jquery/jquery.min.js',
		      exports: 'jQuery'
		    }
		  }
	});
</script>