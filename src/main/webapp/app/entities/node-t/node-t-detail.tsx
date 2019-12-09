import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './node-t.reducer';
import { INodeT } from 'app/shared/model/node-t.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface INodeTDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class NodeTDetail extends React.Component<INodeTDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { nodeTEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            NodeT [<b>{nodeTEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{nodeTEntity.name}</dd>
            <dt>Detail T</dt>
            <dd>
              {nodeTEntity.detailTS
                ? nodeTEntity.detailTS.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === nodeTEntity.detailTS.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/node-t" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/node-t/${nodeTEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ nodeT }: IRootState) => ({
  nodeTEntity: nodeT.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NodeTDetail);
