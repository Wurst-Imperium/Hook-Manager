$(document).ready(function()
{
  $('a[href*=#]').click(function(e)
	{
    if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '')
			&& location.hostname == this.hostname)
		{
      var href = $(this).attr("href"),
        offsetTop = href === "#" ? 0 : $(href).offset().top;
      $('html, body').stop().animate(
			{
        scrollTop: offsetTop
      }, 750);
      e.preventDefault();
    }
  });
});
