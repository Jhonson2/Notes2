package com.example.dellc.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView search;
    private FloatingActionButton fab;
    private MyAdapter myAdapter;
    private RecyclerView recycler_view;
    private List<Note> data=new ArrayList<Note>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
        initAdapter();
        initMotion();

    }

    private void initMotion() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                // 获取触摸响应的方向   包含两个 1.拖动dragFlags 2.侧滑删除swipeFlags
                // 代表只能是向左侧滑删除，当前可以是这样ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT
                int dragFlag;
                int swipeFlag;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                            | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
                    swipeFlag = 0;
                } else {
                    dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
                    swipeFlag = ItemTouchHelper.END;
                }
                return makeMovementFlags(dragFlag, swipeFlag);
            }



            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(data, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(data, i, i - 1);
                    }
                }
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            /**
             * 侧滑删除后会回调的方法
             */

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.END) {
                    DataSupport.deleteAll(Note.class,"date = ?",data.get(position).getDate());
                    initData();
                    myAdapter.notifyDataSetChanged();
                }
            }

            /**
             * 拖动选择状态改变回调
             */

            /*@Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    viewHolder.itemView.setBackgroundColor(Color.GRAY);
                }
            }
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
                viewHolder.itemView.setElevation(18);
            }*/
        });
        // 这个就不多解释了，就这么attach
        itemTouchHelper.attachToRecyclerView(recycler_view);

    }

    private void initAdapter() {
        myAdapter=new MyAdapter(data);
        recycler_view.setAdapter(myAdapter);
        //设置RecyclerView保持固定的大小,提高RecyclerView的性能
        recycler_view.setHasFixedSize(true);
    }

    private void initData() {
        data.clear();
        List<Note> noteList= DataSupport.findAll(Note.class);
        for (int i = 0; i < noteList.size(); i++) {
            Note note=new Note();
            note.setTitle(noteList.get(i).getTitle());
            note.setContent(noteList.get(i).getContent());
            note.setDate(noteList.get(i).getDate());
            data.add(note);
            Log.d("dd", "initData.: "+noteList.get(i).getContent());
        }
    }



    private void initViews() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search= (TextView) findViewById(R.id.search);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        recycler_view= (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        click();
    }

    private void click() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"这是搜索",Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,WriteActivity.class);
                startActivity(intent);
            }
        });

        recycler_view.addOnItemTouchListener(new ItemClickListener(new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //String str1=data.get(position).getTitle()+data.get(position).getContent()+data.get(position).getDate();
                //Toast.makeText(MainActivity.this,str1,Toast.LENGTH_LONG).show();
                String str1=data.get(position).getTitle();
                String str2=data.get(position).getContent();
                String str3=data.get(position).getDate();
                String str []=new String[]{str1,str2,str3};
                EventBus.getDefault().postSticky(str);
                Intent intent=new Intent(MainActivity.this,AlterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this,"这是onItemLongClick",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDoubleClick(View view, int position) {
                //Toast.makeText(MainActivity.this,"这是onItemDoubleClick",Toast.LENGTH_SHORT).show();
            }
        }));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                Toast.makeText(MainActivity.this,"这是编辑",Toast.LENGTH_SHORT).show();
                break;

            case R.id.mag_kind:
                Toast.makeText(MainActivity.this,"这是管理类别",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        myAdapter.notifyDataSetChanged();
    }
}
