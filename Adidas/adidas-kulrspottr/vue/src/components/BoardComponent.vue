<script>
import BoxComponent from "./BoxComponent";
import BoardService from '../services/BoardService';

export default {
  name: "BoardComponent",
  components: { BoxComponent },
  
  data: function() {
    return {
      level: 1,
      boardConfig: {}
    }
  },

  created: function() {
    this.boardConfig = BoardService.initBoard(this.level);
  },

  methods: {
    clickBox: function(value) {
      if (value === this.boardConfig.specialBox) {
        this.level++;
        this.boardConfig = BoardService.initBoard(this.level);
      } else {
        this.$router.push({ name: 'hallOfFame', params: { level: this.level }})
      }
    }
  },

  render: function(createElement) {
    const vm = this;
    const items = [];
    let box = 1;
    items.push(createElement('h6', 'Level ' + vm.level));
    for (let i = 0, l = this.boardConfig.boxesPerAxis; i < l; i++) {
      const rowArray = [];

      for (let a = 0, l = this.boardConfig.boxesPerAxis; a < l; a++) {
        let boxOpts = {
          props: { 
            color: this.boardConfig.hsl.color,
            boxId: box
          },
          on: {
            click: (value) => {
              vm.clickBox(value);
            }
          }
        };

        if (box === this.boardConfig.specialBox) {
          boxOpts.props.color = this.boardConfig.hsl.specialColor;
        }

        const boxComponent = createElement(BoxComponent, boxOpts);
        rowArray.push(boxComponent);
        box++;
      }
      
      items.push(createElement('div', {
        class: 'row'
      }, rowArray));
    }

    items.push(createElement('div', {
      class: 'checkmark'
    }));

    return createElement('div', {
      class: 'board'
    }, items);
  }
};
</script>

<style lang="scss">
.board {
  display: flex;
  flex: 1;
  flex-direction: column;

  h6 {
    text-align: center;
  }
}
.row {
  width: 100%;
  display: flex;
  margin-bottom: 3px;
  flex-grow: 1;
  flex: 1;
}
.checkmark {
  display: none;
  
  &.draw:after {
    animation-duration: 800ms;
    animation-timing-function: ease;
    animation-name: checkmark;
    transform: scaleX(-1) rotate(135deg);
  }
  
  &:after {
    opacity: 1;
    height: 100px;
    width: 100px;
    transform-origin: left top;
    border-right: 2px solid green;
    border-top: 2px solid green;
    content: '';
    left: 50%;
    top: 50%;
    position: absolute;
  }
}
@keyframes checkmark {
  0% {
    height: 0;
    width: 0;
    opacity: 1;
  }
  20% {
    height: 0;
    width: 100px;
    opacity: 1;
  }
  40% {
    height: 100px;
    width: 100px;
    opacity: 1;
  }
  100% {
    height: 100px;
    width: 100px;
    opacity: 1;
  }
}
</style>
